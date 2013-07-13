(ns clojure-defender.geometry-test
  (:use [clojure.test]
        [clojure-defender.physics.geometry]))

(deftest in-rectangle-test
  (testing "is in rectangle"
    (is (= (in-rect? 1 1 0 0 2 2) true))
    (is (= (in-rect? 0 0 0 0 0 0) true))
    (is (= (in-rect? 1 1 0 0 1 1) true)))
  (testing "is not in rectangle"
    (is (= (in-rect? 2 2 0 0 1 1) false))
    (is (= (in-rect? 1 1 0 0 0 0) false))
    (is (= (in-rect? 1 1 0 0 0 1) false))
    (is (= (in-rect? 1 1 0 0 1 0) false))))

(deftest subvect-test
  (testing "can get correct subvect"
    (is (= (sub-vect 0 0 0 0) [0 0]))
    (is (= (sub-vect 0 0 1 1) [1 1]))
    (is (= (sub-vect 1 1 0 0) [-1 -1]))
    (is (= (sub-vect 1 0 0 1) [-1 1]))
    (is (= (sub-vect 0 1 1 0) [1 -1]))))

(deftest normalized-vector-test
  (testing "can get correct ormalized vector"
    (is (= (normalized-vector 1 1) [1 1]))
    (is (= (normalized-vector 2 1) [1 1/2]))
    (is (= (normalized-vector 1 2) [1/2 1]))
    (is (= (normalized-vector -2 1) [-1 1/2]))
    (is (= (normalized-vector 1 -2) [1/2 -1]))
    (is (= (normalized-vector -2 -2) [-1 -1]))))

(deftest rectangle-center-test
  (testing "can get correct rectangle ceter"
    (is (= (rect-center 0 0 2 2) [1 1]))
    (is (= (rect-center 0 0 4 2) [2 1]))
    (is (= (rect-center 1 1 4 2) [3 2]))
    (is (= (rect-center -1 -2 2 4) [0 0]))))

(deftest rectandle-diagonal-length
  (testing "can get correct rectandle diagonal length"
    (is (= (rect-diagonal-length 4 3) 5))
    (is (= (rect-diagonal-length 3 4) 5))))

(deftest rectangle-diagonal-radius-length
  (testing "can get rectangle diagonal radius legth"
    (is (= (rect-radius-length 4 3) 5/2))
    (is (= (rect-radius-length 3 4) 5/2))))

(deftest distance-test
  (testing "can get correct distance"
    (is (= (distance 0 0 3 4) 5))))

(deftest sphere-colision-tets
  (testing "sphere colision"
    (is (= (sphere-colision? 0 0 1 1 1 1) true))
    (is (= (sphere-colision? 0 0 10 0 0 20) true))
    (is (= (sphere-colision? 0 0 3 0 5 2) true))
    (is (= (sphere-colision? 0 0 2 0 5 2) false))))

(deftest object-in-range-test
  (testing "object is in range?"
    (is (= (in-range? 0 0 0 0 10) true))
    (is (= (in-range? 0 0 0 1 1) true))
    (is (= (in-range? 0 0 1 0 1) true))
    (is (= (in-range? 0 0 1 1 1) false))))
