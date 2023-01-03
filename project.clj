(defproject sandbox "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [techascent/tech.ml.dataset "6.033"]
                 [org.clojure/math.numeric-tower "0.0.5"]
                 [twitter-api "1.8.0"]
                 [cheshire "5.11.0"]]
  :main nil
  :profiles {:markov   {:main sandbox.markov}
             :core     {:main sandbox.core}
             :twete    {:main sandbox.twete}}
  :jvm-opts ["-Xmx128m"]
  ;;  :repl-options {:init-ns sandbox.core}
  )
