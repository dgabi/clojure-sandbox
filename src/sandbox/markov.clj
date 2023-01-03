(ns sandbox.markov)


(defn gen-pairs [text]
  (map #(vector (first %) (second %))
       (partition 2 1 (filter #(not= "" %) (clojure.string/split text #"\s+")))))

(defn sanitize-text [text]
  (->> text
       (#(clojure.string/replace % #"[,\.\?:;!#%$@^&\*()<>\/\|-]" ""))
       (#(clojure.string/replace % #"\r\n|\n|\r" " "))))

(defn put-append [word-map pair]
  ; add an element to the hash-map: if key exists will append the element to existing value
  ; if not it will create a key with new value
  (if (contains? word-map (pair 0))
    (assoc word-map (pair 0) (conj (word-map (pair 0)) (pair 1)))
    (assoc word-map (pair 0) (vector (pair 1)))))


(defn build-word-map [pair-list]
  (loop [pairs pair-list word-map {}]
    (if pairs
      (let [pair (first pairs)]
        (recur (next pairs) (put-append word-map pair)))
      word-map)))

(defn generate-text [word-map len]
  (loop [last-word (rand-nth (keys word-map)) p len all-text ""]
    (if (> p 0)
      (let [next-word (rand-nth (word-map last-word))]
        (recur next-word (dec p) (str all-text " " next-word)))
      all-text)))

(defn -main
  "main"
  [file]
  (let [word-map (build-word-map (->> (slurp file)
                                      (sanitize-text)
                                      (gen-pairs)))]
    (println (generate-text word-map 20))))
