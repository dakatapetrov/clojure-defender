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

(def slow (define-debuff (fn [enemy]
                           (dosync
                             (alter enemy update-in [:speed] #(* % 0.5))))
                         (fn [enemy]
                           (dosync
                             (alter enemy update-in [:speed] #(* % 2))))
                         1000))

(def fireball (define-projectile 1.2 40 :yellow))
(def frostball (define-projectile 1.1 45 "#b8cce4"))
(def arcanemissle (define-projectile 1.3 35 :white))
(def slow-frostball (modify-projectile frostball
                                       {:damage 10 :debuff slow :color "#2f4d71"}))
