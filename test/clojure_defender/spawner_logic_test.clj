(ns clojure-defender.spawner-logic-test
  (:use [clojure.test]
        [clojure-defender.data enemies projectiles]
        [clojure-defender.logic.spawner])
  (:require [clojure-defender.globals :as gl]))

(deftest spawner-logic-test
  (let [spawner {:x 0 :y 0 :enemies [orc assassin] :cooldown 50 :ac (atom 0)}]
    (step-spawner spawner)
    (testing "spawns enemy"
      (is (first @gl/enemies)))
    (testing "resets cooldown"
      (is (= @(:ac spawner) 50)))))
