(ns app.app
  (:require [keechma.next.controllers.router]
            [keechma.next.controllers.dataloader]
            [keechma.next.controllers.subscription]
            ["react-dom" :as rdom]
            [app.controllers.persons]))

(defn page-equal? [router page] (let [route-page (:page router)]
                                  (= route-page page)))

(def app
  {:keechma.subscriptions/batcher rdom/unstable_batchedUpdates,
   :keechma/controllers
   {:router {:keechma.controller/params true,
             :keechma.controller/type :keechma/router,
             :keechma/routes [["" {:page "home"}] ":page" ":page/:subpage"]}
    
    :entitydb   {:keechma.controller/params true
                 :keechma.controller/type   :keechma/entitydb}

    :dataloader {:keechma.controller/params true,
                 :keechma.controller/type :keechma/dataloader}
    
    :persons #:keechma.controller {:params (fn [{:keys [router]}]
                                             (page-equal? router "person"))
                                   :deps   [:router :entitydb]}
    
    }})