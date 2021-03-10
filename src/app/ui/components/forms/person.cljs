(ns app.ui.components.forms.person
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.lib :refer [defnc]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.core :refer [with-keechma dispatch]]
            [keechma.next.helix.classified :refer [defclassified]]
            [keechma.next.helix.core :refer [with-keechma use-meta-sub dispatch call use-sub]]

            [app.ui.components.inputs :refer [wrapped-input]]))

(defnc PersonFormRenderer [props]
  (d/div {:class "m-auto min-w-full p-8 text-white border-solid border-gray-500 border"}
         (d/form {:on-submit (fn [e]
                               (.preventDefault e)
                               (dispatch props :persons :keechma.form/submit))}

                 (d/p {:class "text-sm text-grayLight text-left w-full mt-6"} "First name")
                 (wrapped-input {:keechma.form/controller :persons
                                 :input/type              :text
                                 :input/attr              :first-name
                                 :placeholder             "First name"})

                 (d/p {:class "text-sm text-grayLight text-left w-full mt-6"} "Last name")
                 (wrapped-input {:keechma.form/controller :persons
                                 :input/type              :text
                                 :input/attr              :last-name
                                 :placeholder             "Last name"})

                 (d/button {:class "block margin-auto mx-auto border w-56 px-4 py-3 rounded-sm text-md font-medium text-white bg-gray-500 hover:bg-gray-600
                                         md:py-4 md:mt-8"} "Submit"))))

(def PersonForm (with-keechma PersonFormRenderer))