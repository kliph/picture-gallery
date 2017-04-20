(ns picture-gallery.components.registration
  (:require [reagent.core :as r]
            [picture-gallery.components.common :as c]))

(defn registration-form []
  (let [fields (r/atom {})]
    (fn []
      [c/modal
       [:div "Picture Gallery Registration"]
       [:div
        [:div.well.well-sm
         [:strong "* required field"]]
        [c/text-input "name" :id "enter a user name" fields]
        [c/password-input "password" :pass "enter a password" fields]
        [c/password-input "password" :pass-confirm "confirm the password" fields]]
       [:div
        [:button.btn.btn-primary "Register"]
        [:button.btn.btn-danger "Cancel"]]])))
