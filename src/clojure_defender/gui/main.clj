(ns clojure-defender.gui.main)

(use 'clojure.repl)
(use '[seesaw core color graphics behave keymap])
(require '[seesaw.mouse :as mouse])
(require '[clojure-defender.globals :as gl])
(require '[clojure-defender.data.buildings :as db])
(use 'clojure-defender.levels.level-01)
(use '[clojure-defender.logic.building :only [build-or-destroy]])
(use 'clojure-defender.logic.main)

(defn display [fr content]
  (config! fr :content content)
  content)

(defn draw-play-area
  [g]
  (let [{:keys [x y width height color]} world]
  (draw g (rect x y width height) (style :background color))))


(defn draw-paths
  [g]
  (doseq [path @gl/paths]
    (let [{:keys [x y color]} path]
      (draw g (rect x y gl/path-size) (style :background color)))))

(defn draw-defend-points
  [g]
  (doseq [protect-point @gl/defend-points]
    (let [{:keys [x y color]}  protect-point]
      (draw g (rect x y gl/path-size) (style :background :red)))))

(defn draw-build-areas
  [g]
  (doseq [build-area @gl/build-areas]
    (let [{:keys [x y color]}  build-area]
      (draw g (rect x y gl/building-size) (style :background color)))))

(defn draw-enemies
  [g]
  (doseq [enemy @gl/enemies]
    (let [{:keys [x y color]} @enemy]
      (draw g (rect x y gl/enemy-size) (style :background color)))))

(defn draw-buildings
  [g]
  (doseq [building @gl/buildings]
    (let [{:keys [x y color]} building]
      (draw g (rect x y gl/building-size) (style :background color)))))

(defn draw-projectiles
  [g]
  (doseq [projectile @gl/projectiles]
    (let [{:keys [x y color]} @projectile]
      (draw g (circle x y gl/projectile-size) (style :background color)))))


(defn draw-world
  [c g]
  (draw-play-area g)
  (draw-paths g)
  (draw-defend-points g)
  (draw-build-areas g)
  (draw-buildings g)
  (draw-enemies g)
  (draw-projectiles g))

(defn make-panel []
  (border-panel
    :west (vertical-panel :items [(button :text "Play" :class :play-button)
                                  (button :text "Pause" :class :pause-button)
                                  (button :text "Fire" :class :fire-tower)
                                  (button :text "Frost" :class :frost-tower)
                                  (button :text "Arcane" :class :arcane-tower)
                                  (button :text "Ultimate" :class :ultimate)
                                  (label :text (str "Lives " @gl/lives) :class :lives)
                                  (label :text (str "Funds " @gl/funds) :class :funds)])
    :center (canvas :paint draw-world
                    :class :world
                    :background :black
                    :cursor :hand)))

(defn redisplay [root]
  (dosync
    (config! (select root [:.world]) :paint draw-world)
    (config! (select root [:.lives]) :text (str "Lives " @gl/lives))
    (config! (select root [:.funds]) :text (str "Funds " @gl/funds))))

(defn create-gui
  []
  (-> gl/main-frame show!)
  (display gl/main-frame (make-panel))
  (listen (select gl/main-frame [:.play-button])
          :action
          (fn [e] (reset! gl/playing? true)))
  (listen (select gl/main-frame [:.pause-button])
          :action
          (fn [e] (reset! gl/playing? false)))
  (listen (select gl/main-frame [:.fire-tower])
          :action
          (fn [e] (reset! gl/current-building db/fire-tower)))
  (listen (select gl/main-frame [:.frost-tower])
          :action
          (fn [e] (reset! gl/current-building db/frost-tower)))
  (listen (select gl/main-frame [:.arcane-tower])
          :action
          (fn [e] (reset! gl/current-building db/arcane-tower)))
  (listen (select gl/main-frame [:.ultimate])
          :action
          (fn [e] (reset! gl/current-building db/ultimate-tower)))
  (listen (select gl/main-frame [:.world])
          :mouse-clicked
          (fn [e] (let [[x y] (mouse/location e)]
                    (build-or-destroy x y)))))

(defn run
  []
  (loop [painter (atom (future (true)))]
    (when @gl/playing?
      (play))
    (when (realized? @painter)
      (reset! painter (future (redisplay gl/main-frame))))
    (Thread/sleep 3)
    (recur painter)))
