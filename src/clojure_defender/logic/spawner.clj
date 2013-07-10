(ns clojure-defender.logic.spawner)

(require '[clojure-defender.globals :as gl])

(defn- on-cooldown?
  [spawner]
  (let [ac (:ac spawner)]
    (pos? @ac)))

(defn- reset-cooldown
  [spawner]
  (let [{:keys [ac cooldown]} spawner]
    (reset! ac cooldown)))

(defn- spawn
  [spawner]
  (let [{:keys [x y enemies]} spawner
        enemy (rand-nth enemies)]
  (swap! gl/enemies
           conj
           (ref (conj enemy {:x x :y y})))))

(defn step-spawner
  [spawner]
  (when-not (on-cooldown? spawner)
    (dosync
      (spawn spawner)
      (reset-cooldown spawner))))
