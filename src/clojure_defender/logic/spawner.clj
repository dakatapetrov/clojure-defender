(ns clojure-defender.logic.spawner)

(require '[clojure-defender.globals :as gl])

(defn spawn
  []
  (doseq [spawner @gl/spawners]
    (let [ac (:ac spawner)]
      (when-not (pos? @ac)
        (let [{:keys [x y cooldown]} spawner
              enemy (rand-nth (:enemies spawner))]
          (swap! gl/enemies
                 conj
                 (ref (conj enemy {:x x :y y})))
          (reset! ac cooldown))))))
