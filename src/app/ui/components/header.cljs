(ns app.ui.components.header
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [<>]]
            [helix.hooks :as hooks]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.lib :refer [defnc]]))

(defnc Header [{title :page-title :as props}]
   (d/div {:class "flex flex-row bg-gray-900 py-8 px-16 text-white"}
          (d/div {:class "flex w-1/6 justify-start items-center font-thin cursor-pointer hover:text-gray-400"
                  :on-click #(router/back! props :router)} "< Back")
          (d/div {:class "flex w-4/6 justify-center items-center text-4xl font-hairline"} title)
          (d/div {:class "flex w-1/6 justify-end items-center font-thin"})))