(ns scheduler.core
  (:require [amazonica.aws.ec2 :refer :all]))

;;so, we can get the currently running instances....

(defn instances
  "Gives us a sequence of currently running instances.
   Returns a sequence of amazonica maps describing each instance."
  []
  (->> (describe-instances)
       :reservations
       (mapcat :instances)
     ))

;;very simple projection of the instance information
;;relative to its current state, the launch-time, etc.
(def instance-summary
  (juxt :key-name :state :launch-time))

(defn instance-states [xs]
  (map (juxt :key-name :state)))

;;where do rules live?
;;we can stick them in s3....
;;assuming you have a rules bucket...
;;and this isn't running often...
;;other option is a dynamodb table like AWS does.
;;Allow users to muck with it..

;;for now, lets assume there are only 2 rules.
