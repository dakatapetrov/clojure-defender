(ns clojure-defender.data.projectiles)

(defn- define-debuff
  [put clear duration]
  {:put put :clear clear :duration duration})

(defn- define-projectile
  [speed damage color]
  {:speed speed :damage damage :color color})

(defn- apply-debuff
  [debuff projectile]
  (conj projectile {:debuff debuff}))

(defn- modify-projectile
  [projectile attributes]
  (conj projectile attributes))

(def slow (define-debuff (fn [enemy] (dosync
                                       (alter enemy update-in [:speed] #(* % 0.5))))
                         (fn [enemy] (dosync
                                       (alter enemy update-in [:speed] #(* % 2))))
                         2000))

(def fireball (define-projectile 0.3 40 :yellow))
(def frostball (define-projectile 0.25 45 :aqua))
(def arcanemissle (define-projectile 0.35 35 :white))
(def slow-frostball (modify-projectile frostball {:damage 10 :debuff slow :color :blue}))
