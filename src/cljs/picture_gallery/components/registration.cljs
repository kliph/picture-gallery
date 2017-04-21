(ns picture-gallery.components.registration
  (:require [reagent.core :as r]
            [ajax.core :as ajax]
            [reagent.session :as session]
            [picture-gallery.validation :refer [registration-errors]]
            [picture-gallery.components.common :as c]))

(defn register! [fields errors]
  (reset! errors (registration-errors @fields))
  (when-not @errors
    (ajax/POST "/register"
               {:params @fields
                :handler #(do
                            (session/put! :identity (:id @fields))
                            (reset! fields {}))
                :error-handler #(reset!
                                 errors
                                 {:server-error (get-in % [:response :message])})})))

(defn registration-form []
  (let [fields (r/atom {})
        error (r/atom nil)]
    (fn []
      [c/modal
       [:div "Picture Gallery Registration"]
       [:div
        [:div.well.well-sm
         [:strong "* required field"]]
        [c/text-input "name" :id "enter a user name" fields]
        [c/password-input "password" :pass "enter a password" fields]
        [c/password-input "password" :pass-confirm "confirm the password" fields]
        (when-let [error (:server-error @error)]
          [:div.alert.alert-danger error])]
       [:div
        [:button.btn.btn-primary
         {:on-click #(register! fields error)}
         "Register"]
        [:button.btn.btn-danger
         {:on-click #(session/remove! :modal)}
         "Cancel"]]])))

(defn registration-button []
  [:a.btn
   {:on-click #(session/put! :modal registration-form)}
   "register"])
