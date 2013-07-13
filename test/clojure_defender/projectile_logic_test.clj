(ns clojure-defender.projectile-logic-test
  (:use [clojure.test]
        [clojure-defender.data enemies projectiles]
        [clojure-defender.logic.projectile])
  (:require [clojure-defender.globals :as gl]))

(deftest projectile-logic-test
  (let [enemy (ref (conj orc {:x 1 :y 1}))
        projectile (ref (conj slow-frostball {:x 5 :y 5 :enemy enemy}))]
    (swap! gl/projectiles conj projectile)
    (step-projectile projectile)
    (testing "pojectile does damage on colision"
      (is (< (:hp @enemy) 120)))
    (testing "projectile applies debuff"
      (is (:debuff @enemy)))))
