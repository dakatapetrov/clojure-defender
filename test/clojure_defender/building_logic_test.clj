(ns clojure-defender.building-logic-test
  (:use [clojure.test]
        [clojure-defender.data enemies buildings]
        [clojure-defender.logic.building])
  (:require [clojure-defender.globals :as gl]))

(deftest shooting-test
  (let [enemy1 (ref (conj orc {:x 0 :y 0}))
        enemy2 (ref (conj assassin {:x 0 :y 0}))
        tower (conj fire-tower {:x 1 :y 1 :ac (atom 0)})]
    (swap! gl/enemies conj enemy1 enemy2)
    (shoot tower)
    (testing "spawns projectile targetting correct enemy unit"
      (is (=  (:enemy @(first @gl/projectiles)) enemy2)))
    (testing "resets building cooldown"
      (is (= @(:ac tower) 150)))))

(deftest build-or-destroy-tets
  (let [build-area {:x 0 :y 0}]
    (swap! gl/build-areas conj build-area)
    (reset! gl/funds 200.0)
    (build-or-destroy 1 1)
    (testing "can build"
      (is (first @gl/buildings)))
    (testing "takes funds on build"
      (is (= @gl/funds 100.0)))
    (build-or-destroy 1 1)
    (testing "can destroy"
      (is (not (first @gl/buildings))))
    (testing "returns funds on destroy"
      (is (= @gl/funds 180.0)))))
