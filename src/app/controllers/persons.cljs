(ns app.controllers.persons
  (:require [app.validators :as v]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]
            [keechma.next.controller :as ctrl]
            [keechma.next.toolbox.logging :as l]
            [keechma.next.controllers.entitydb :as edb]
            [keechma.next.controllers.form :as form]
            [keechma.next.controllers.pipelines :as pipelines]))

(def persons
  [{:id 1 :name "Ivan"    :surname "Markovic"}
   {:id 2 :name "Dominik" :surname "Petrovic"}
   {:id 3 :name "Mac"     :surname "Glavonja"}])

(derive :persons ::pipelines/controller)

(def load-persons
  (-> (pipeline! [_ {:keys [deps-state*] :as ctrl}]
                 ;; tu bi dosao podataak sa APIa
                 (edb/insert-collection! ctrl :entitydb :person :person/list persons))
      (pp/set-queue :load-persons)))

(def update-persons
  (-> (pipeline! [value {:keys [state* deps-state*] :as ctrl}]
                 (let [persons (edb/get-collection (:entitydb @deps-state*) :person/list)
                       number-of-persons (count persons)]
                   (pipeline! [value {:keys [deps-state*] :as ctrl}]
                              (edb/insert-collection! ctrl :entitydb :person :person/list (assoc persons number-of-persons
                                                                                                 {:name (:first-name value)
                                                                                                  :surname (:last-name value)
                                                                                                  :id (inc number-of-persons)}))
                              (pp/swap! state* update :is-form-open? not))))
      
      (pp/set-queue  :update-persons)))

(def pipelines
  {:keechma.on/start load-persons
   :keechma.form/submit-data update-persons
   :toggle   (pipeline! [_ {:keys [state* deps-state*] :as ctrl}]
                        (pp/swap! state* update :is-form-open? not))})

(defmethod ctrl/prep :persons [ctrl]
  (pipelines/register ctrl pipelines))

(defmethod ctrl/prep :persons [ctrl]
  (pipelines/register ctrl
                      (form/wrap pipelines
                                 (v/to-validator {:first-name  [:not-empty]
                                                  :last-name   [:not-empty]}))))

(defmethod ctrl/start :persons [_ state _ _]
  {:is-form-open? nil})

(defmethod ctrl/derive-state :persons [_ state {:keys [entitydb]}]
  {:persons (edb/get-collection entitydb :person/list)
   :brada2 (:is-form-open? state)})