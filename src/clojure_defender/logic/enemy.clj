(ns clojure-defender.logic.enemy)

(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.physics.geometry)

(defn move-enemy
  [enemy]
  (let [{:keys [speed hp x y]} @enemy
        on-path (on-object x y @gl/paths)
        [px py] (:direction on-path)
        on-defend-point (on-object x y @gl/defend-points)
        movex (* speed px)
        movey (* speed py)]
    (if (or (<= hp 0) on-defend-point)
      (swap! gl/enemies disj enemy)
      (dosync
        (alter enemy update-in [:x] #(+ % movex))
        (alter enemy update-in [:y] #(+ % movey))))))
