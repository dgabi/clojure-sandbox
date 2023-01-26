(ns sandbox.transformers)

;; functions for data manipulations


(defn read-data
  "read a csv and create a map for each row using the header row as keys "
  [filename]
  (let [col (map #(str/split % #"\t")
                 (-> (slurp filename)
                     (str/split-lines)))]
    (map #(zipmap (map keyword (first col)) %) (rest col))))

(defn get-first
  "return the first column of a bidimensional collection"
  [col] (map (fn [item] (first item)) col))

(defn get-last
  "return the last column of a bidimentional collection"
  [col]
  (map (fn [item] (last item)) col))


(defn count-values
  "return cardinality for each map value in the collection. sorts the collection by key"
  [col]
  (sort-by first (map (fn [item] (do [(first item) (count (second item))])) col)))

(defn group-by-column-by-column
  "groups by one column and groups each group by second column - ie group by date then by location"
  [dataset column1 column2]
  (map (fn [arg] (hash-map (first arg)
                           (sort-by first (map #(identity [(first %) (count (last %))])
                                               (group-by column1 (last arg))))))
       (group-by column2 dataset)))

(defn counts-by-column
  ""
  [dataset column]
  (reverse (sort-by last (count-values (group-by column dataset)))))

(defn trunc
  "truncate"
  [some-string some-size]
  (subs some-string 0 (min (count some-string) some-size)))
