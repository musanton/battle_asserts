(ns battle-asserts.issues.excel-sheet-column-number
  (:require [clojure.test.check.generators :as gen]
            [faker.generate :as faker]))

(def level :easy)

(def description "Given a column title (as it appears in an Excel sheet), return its corresponding column number.
                 For example: A -> 1, B -> 2, C -> 3, ...  Z -> 26, AA -> 27, AB -> 28")

(defn arguments-generator []
  (let [letters (map #(char (+ % (int \A))) (range 0 26))]
    (letfn [(permutation [length]
              (apply str (repeatedly length #(rand-nth letters))))]
      (gen/tuple (gen/bind (gen/choose 1 4) #(gen/return (permutation %)))))))

(def test-data
  [{:expected 1
    :arguments ["A"]}
   {:expected 26
    :arguments ["Z"]}
   {:expected 666
    :arguments ["YP"]}
   {:expected 2458
    :arguments ["CPN"]}
   {:expected 24568
    :arguments ["AJHX"]}])

(defn pow [base exp]
  (apply * (repeat exp base)))

(defn solution [s]
  (->>
   s
   clojure.string/reverse
   char-array
   (map-indexed #(* (- (int %2) 64) (pow 26 %1)))
   (apply +)))
