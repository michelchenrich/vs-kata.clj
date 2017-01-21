(ns vs-tests
  (:use clojure.test vs))

(defn- price [movie days] (:price (rental movie days)))
(defn- renter-points [movie days] (:renter-points (rental movie days)))

(let [regular-movie (regular-movie "" "")
      childrens-movie (childrens-movie "" "")
      new-release (new-release "" "")]

  (deftest regular-movie-pricing
    (testing "rented for one day"
      (is (= 1.75 (price regular-movie 1))))
    (testing "rented for two days"
      (is (= 1.75 (price regular-movie 2))))
    (testing "rented for three days incurs penalty"
      (is (= 3.25 (price regular-movie 3))))
    (testing "rented for four days incurs two penalties"
      (is (= 4.75 (price regular-movie 4)))))

  (deftest childrens-movie-pricing
    (testing "rented for one day"
      (is (= 1.25 (price childrens-movie 1))))
    (testing "rented for two days"
      (is (= 1.25 (price childrens-movie 2))))
    (testing "rented for three days"
      (is (= 1.25 (price childrens-movie 3))))
    (testing "rented for four days incurs penalty"
      (is (= 2.25 (price childrens-movie 4))))
    (testing "rented for four days incurs two penalties"
      (is (= 3.25 (price childrens-movie 5)))))

  (deftest new-release-pricing
    (testing "rented for one day"
      (is (= 3.0 (price new-release 1))))
    (testing "rented for two days"
      (is (= 6.0 (price new-release 2))))
    (testing "rented for three days"
      (is (= 9.0 (price new-release 3))))))


(let [regular-movie (regular-movie "" "")
      childrens-movie (childrens-movie "" "")
      new-release (new-release "" "")]

  (deftest regular-movie-renter-points
    (testing "rented for one day"
      (is (= 1.0 (renter-points regular-movie 1))))
    (testing "rented for two days"
      (is (= 1.0 (renter-points regular-movie 2))))
    (testing "rented for three days"
      (is (= 1.0 (renter-points regular-movie 3)))))

  (deftest childrens-movie-renter-points
    (testing "rented for one day"
      (is (= 1.0 (renter-points childrens-movie 1))))
    (testing "rented for two days"
      (is (= 1.0 (renter-points childrens-movie 2))))
    (testing "rented for three days"
      (is (= 1.0 (renter-points childrens-movie 3))))
    (testing "rented for four days"
      (is (= 1.0 (renter-points childrens-movie 4)))))

  (deftest new-release-renter-points
    (testing "rented for one day"
      (is (= 1.0 (renter-points new-release 1))))
    (testing "rented for two days"
      (is (= 2.0 (renter-points new-release 2))))
    (testing "rented for three days"
      (is (= 3.0 (renter-points new-release 3))))))

(deftest rental-statement
  (testing "generate a statement"
    (is (= {:items         [{:title "Title 1", :year "2017", :days 1, :price 3.0, :renter-points 1.0}
                            {:title "Title 2", :year "2018", :days 2, :price 1.75, :renter-points 1.0}
                            {:title "Title 3", :year "2019", :days 3, :price 1.25, :renter-points 1.0}],
            :total         6.0,
            :renter-points 3.0}
           (statement [(rental (new-release "Title 1", "2017"), 1),
                       (rental (regular-movie "Title 2", "2018"), 2),
                       (rental (childrens-movie "Title 3", "2019"), 3)])))))

(run-tests 'vs-tests)