(ns app.ui.pages.home
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.lib :refer [defnc]]
            [keechma.next.helix.core :refer [with-keechma]]
            [keechma.next.helix.classified :refer [defclassified]]
            [keechma.next.controllers.router :as router]))

(defclassified HomepageWrapper :div "h-screen w-screen flex bg-gray-200")

(defnc HomeRenderer [props]
  ($ HomepageWrapper
     (d/div {:class "flex flex-1 flex-col items-center justify-center px-2"}
            "DEMO APP"
            (d/button {:class "flex rounded w-2/3 justify-center my-8 py-2 px-8 bg-gray-600 hover:bg-gray-700 text-white text-xl font-thin"
                      :on-click #(router/redirect! props :router {:page "person"})}
                      "Persons"))))

(def Home (with-keechma HomeRenderer))