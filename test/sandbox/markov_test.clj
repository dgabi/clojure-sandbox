(ns sandbox.markov-test
  (:require [clojure.test :refer :all]
            [sandbox.markov :as markov]))


(deftest sanitize
  (testing "replace ?"
    (is (= "test qm" (markov/sanitize-text "?test? ?qm?"))))
  (testing "replace !"
    (is ( = "test excl" (markov/sanitize-text "!test! excl!"))))
  (testing "replace ."
    (is (= "test nodot" (markov/sanitize-text "test. nodot"))))
  (testing "replace #"
    (is ( = " sandbox bar" (markov/sanitize-text "# sandbox bar")))))



(deftest gen-pairs-test
  (testing "generate from 1 word"
    (is (= '(["one"] ( markov/gen-pairs "one")))))
  (testing "generate from 3 words"
    (is (= '(["one" "two"] ["two" "three"]) ( markov/gen-pairs "one two three")))))



(deftest put-append-test
  (testing "put in empty map"
    (is (= {:a [:b]} (markov/put-append {} [:a :b]))))
  (testing "put in non-empty-map new key"
    (is (= {:a [:b] :c [:d]} (markov/put-append {:a [:b]} [:c :d]))))
  (testing "append to non-empty-map existing key"
    (is (= {:a [ :b ] :c [:d :e]} (markov/put-append {:a [:b] :c [:d]} [:c :e]))))) 


(deftest build-word-map
  (testing "1"
    (is (= {:a [:b] :c [:d :e]} (markov/build-word-map [[:a :b] [:c :d] [:c :e]]))))
  (testing "2"
    (is (= {:a [:b] :c [:d :e :f :g :h]} (markov/build-word-map [[:a :b] [:c :d] [:c :e] [:c :f] [:c :g] [:c :h]])))))
