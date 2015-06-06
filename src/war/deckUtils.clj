
(def suitMap {:suit ["club" "diamond" "heart" "spade"]})
(def valueMap {:value [2 3 4 5 6 7 8 9 10 \J \Q \K \A]})
(def standardDeckParamMap [suitMap valueMap])

(defn addSinglePermValue
  [paramName paramValue currentDeck]
;  (println paramName " " paramValue)
;  (println (map #(into %1 {paramName paramValue}) currentDeck))
  (map #(into %1 {paramName paramValue}) currentDeck)
)

(defn getValueList
  [paramMap]
  ;(println paramMap)
  ((first (keys paramMap)) paramMap)
)

(defn addOnePermutation
  ([parameterMap currentDeck]
   ;; parameterMap looks like {:name [val1 val2]}
   ;; currentDeck looks like [{:prop a} {:prop b} {:prop c}]
   ;; I'll need to do a double reduce?
   ;; First one to add a value to each in currentDeck
   ;; Second one to do that for each value and add them together
   (reduce #(into (addSinglePermValue 
                        (first (keys parameterMap))
                        %2
                        currentDeck) %1)
           []
           (getValueList parameterMap))
  )
  ([parameterMap] (addOnePermutation parameterMap [{}]))
  
)

(defn generateDeckFromPermutations
  "This function takes a map of parameter names and
  value lists, and will create a deck with one of every
  permutation of these values. For instance:
  (generateDeckFromPermutations {:suit [\"club\" \"heart\"]
  :value [1 2]}) should return [{:suit club :value 1} {:suit heart
  :value 1} {:suit club :value 2} {:suit heart :value 2}]" 
  [parameterMapList]
  (reduce #(addOnePermutation %2 %1) [{}] parameterMapList)
)

(defn generateStandardDeck
  []
  (generateDeckFromPermutations standardDeckParamMap)
)





(require '[clojure.test :as test])
(test/testing "Deck info"
  (test/testing "deck generation function exists"
    (test/is (resolve 'generateStandardDeck)))
  (test/testing "deck gen function returns a deck"
    (test/is (not (nil? (generateStandardDeck)))))
  (test/testing "deck is the right size"
    (test/is (= 52 (count (generateStandardDeck)))))
  (test/testing "right number of each suit"
    (test/is (= 13 (count (filter #(= (:suit %1) "heart") (generateStandardDeck)))))))
