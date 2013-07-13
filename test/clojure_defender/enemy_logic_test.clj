(ns clojure-defender.enemy-logic-test
  (:use [clojure.test]
        [clojure-defender.data enemies buildings]
        [clojure-defender.logic.enemy])
  (:require [clojure-defender.globals :as gl]))

(deftest enemy-logic-test
  (let [enemy1 (ref (conj orc {:x 0 :y 0}))
        enemy2 (ref (conj assassin {:x 0 :y 0 :hp 0}))
        enemy3 (ref (conj assassin {:x 100 :y 100}))
        path1 {:x 90 :y 90 :direction [1 1]}
        path2 {:x 0 :y 0 :direction [1 1]}
        defend-point {:x 90 :y 90}]
    (swap! gl/paths conj path1 path2)
    (swap! gl/defend-points conj defend-point)
    (reset! gl/lives 1)
    (reset! gl/funds 0.0)
    (step-enemy enemy1)
    (testing "can move enemy"
      (is (= (:x @enemy1) 0.3))
      (is (= (:y @enemy1) 0.3)))
    (swap! gl/enemies enemy2)
    (step-enemy enemy2)
    (testing "kills enemy"
      (is (not (first @gl/enemies))))
    (testing "loots enemy"
      (is (= @gl/funds 20.0)))
    (swap! gl/enemies enemy3)
    (step-enemy enemy3)
    (testing "kills enemy"
      (is (not (first @gl/enemies))))
    (testing "player loses life"
      (is (= @gl/lives 0)))))

