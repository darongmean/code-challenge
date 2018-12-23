(defproject code-challenge "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[clj-http "3.9.1"]
                 [com.taoensso/timbre "4.10.0"]
                 [expound "0.7.2"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/core.async "0.4.490"]


                 ;; see https://github.com/dakrone/clj-http#optional-dependencies
                 [cheshire "5.8.1"]]

  :main code-challenge.core

  :profiles {:dev     {:dependencies [[orchestra "2018.12.06-2"]

                                      ;; see https://github.com/venantius/ultra/issues/90#issuecomment-440776254
                                      [org.clojure/core.rrb-vector "0.0.13"]]

                       :plugins      [[com.jakemccrary/lein-test-refresh "0.23.0"]

                                      ;; see https://github.com/venantius/ultra/issues/90#issuecomment-440776254
                                      [org.clojure/core.rrb-vector "0.0.13"]
                                      [venantius/ultra "0.5.2" :exclusions [org.clojure/clojure org.clojure/core.rrb-vector]]]}

             :uberjar {:aot :all}}

  :repl-options {:init-ns code-challenge.core}

  :target-path "target/%s")
