(ns app.ui.pages.person
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$ <>]]
            [keechma.next.toolbox.logging :as l]
            [keechma.next.helix.lib :refer [defnc]]
            [keechma.next.controllers.router :as router]
            [app.ui.components.header :refer [Header]]
            [app.ui.components.forms.person :refer [PersonForm]]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.helix.classified :refer [defclassified]]))

(defnc TableItem [props]
  (d/tr {:class "border-b border-solid border-gray-700 cursor-pointer bg-gray-600 hover:bg-gray-800"}
        (d/td {:class "pl-2 py-2 text-white pl-16"} (:first-name props))
        (d/td {:class "pl-2 py-2 text-white pl-16"} (:last-name  props))))

(defnc PersonRenderer [props] 
  (let [data (use-sub props :persons)
        persons (:persons data)
        brada (:brada2 data)]
  (d/div {:class "bg-gray-300"}
   ($ Header {:page-title "Person page"
              &            props})

   (d/div {:class "w-2/3 mx-auto"}

          (if brada
            ($ PersonForm)

            (d/table {:class "w-full mt-8"}
                     (d/thead (d/tr {:class "border-b border-t border-solid border-yellow-500 bg-gray-700 text-white"}
                                    (d/th {:class "w-1/2 px-4 py-2 font-base border-l border-r border-solid border-yellow-500"} "First name")
                                    (d/th {:class "w-1/2 px-4 py-2 font-base border-l border-r border-solid border-yellow-500"} "Last name")))
                     (d/tbody
                      (map (fn [person]
                             (let [name (:name person)
                                   surname (:surname person)
                                   id      (:id person)]

                               ($ TableItem {:first-name name
                                             :last-name surname
                                             :key       id
                                             &        props})))
                           persons))))

          (d/button {:class (str (if brada "bg-yellow-400 hover:bg-yellow-600" "bg-gray-700 hover:bg-gray-600") " rounded my-8 py-2 px-8 text-white text-xl font-thin focus:outline-none")
                     :on-click #(dispatch props :persons :toggle)} (if brada "Go back" "Add new person"))))))

(def Person (with-keechma PersonRenderer))