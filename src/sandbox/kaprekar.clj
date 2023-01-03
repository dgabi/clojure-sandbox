(ns kaprekar
  (:require [clojure.string :as str])
  (:require [clojure.math.numeric-tower :as math]))


(defn get-digits [number]
  (map #(Integer/parseInt %) (str/split (str number) #"")))

(defn get-digits-diff [n]
  (let [digits (sort (get-digits n))]
       (- (generate-number (reverse digits))
          (generate-number digits))))

(defn generate-number [v]
  (reduce + (map-indexed
             (fn [idx itm] (* itm (math/expt 10 idx)))
             (reverse v))))

(defn kaprekar-sequence [n]
 (loop [x (get-digits-diff n)
        v (list n)]
   (if (= (get-digits-diff x) x)
     (reverse (cons x v))
     (recur (get-digits-diff x)
            (cons x v)
            ))))
(def -main "main" []
  (frequencies
   (sort
    (map count
         (map kaprekar-sequence (range 1000 1999))))))
