(ns clojure-defender.logic.enemy)

(use 'clojure-defender.physics.enemy)

(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.physics.geometry)

(defn step-enemy
  [enemy]
  (let [{:keys [speed hp x y]} @enemy
        on-path (on-object x y @gl/paths)
        [px py] (:direction on-path)
        on-defend-point (on-object x y @gl/defend-points)
        movex (* speed px)
        movey (* speed py)]
    (if (or (<= hp 0) on-defend-point)
      (kill-enemy enemy)
      (move-enemy enemy movex movey))))
