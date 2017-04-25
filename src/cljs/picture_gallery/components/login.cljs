(ns picture-gallery.components.login
  (:require [reagent.core :refer [atom]]
            [reagent.session :as session]
            [goog.crypt.bas64 :as b64]
            [clojure.string :as s]
            [ajax.core :as ajax]
            [picture-gallery.components.common :as c]))

(defn encode-auth [user pass]
  (->> (str user ":" pass)
       (b64/encodeString)
       (str "Basic ")))

(defn login! [fields error]
  (let [{:keys [id pass]} @fields]
    (reset! error nil)
    (ajax/POST "/login"
               {:headers {"Authorization" (encode-auth
                                           (string/trim id)
                                           pass)}
                :handler #(do
                            (session/remove! :modal)
                            (session/put! :identity id)
                            (reset! fields nil))
                :error-handler #(reset! error
                                        (get-in % [:response :message]))})))
