package com.beattheheat.beatthestreet.Networking.OC_API;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 24-Oct-17.
 */

public class OCRoute {
    private int routeNo;
    private List<String> routeNames;
    private List<OCTrip> trips;

    public OCRoute(int routeNo, List<String> routeNames, List<OCTrip> trips) {
        setRouteNo(routeNo);
        setRouteNames(routeNames);
        setTrips(trips);
    }

    public OCRoute(int routeNo, List<String> routeNames) {
        this(routeNo, routeNames, new ArrayList<OCTrip>());
    }


    /** The GTFS entry is assumed to be from the "trips.txt" table
     *
     * Example:
     *
     * route_id	 service_id	                trip_id	                            trip_headsign	direction_id  block_id
     * 6-277	 SEPT17-SEPDA17-Weekday-21	49754901-SEPT17-SEPDA17-Weekday-21	Rockcliffe	    1	          5778334
     *
     */
    public static void LoadRoute(GTFS gtfs, String gtfsEntry) {
        String[] entries = gtfsEntry.split(",");
        int routeNo = Integer.parseInt(entries[0].split("-")[0]);

        String tripId = entries[2];
        String routeName = entries[3];
        OCTrip tripToAdd = null;

        // Check if the trip exists yet
        if (gtfs.tripTable.containsKey(tripId)) {
            tripToAdd = gtfs.tripTable.get(tripId);
        }
        else {
            throw new GTFSException("Unable to find trip ID: " + tripId + " for route: " + routeNo);
        }

        // Check if this route has already been entered
        if (gtfs.routeTable.containsKey(routeNo)) {
            gtfs.routeTable.get(routeNo).addRouteName(routeName);
            gtfs.routeTable.get(routeNo).addTrip(tripToAdd);
        }
        else {
            List<String> routeNames = new ArrayList<String>();
            routeNames.add(routeName);
            List<OCTrip> trips = new ArrayList<OCTrip>();
            trips.add(tripToAdd);

            gtfs.routeTable.put(routeNo, new OCRoute(routeNo,routeNames, trips));

        }
    }




    /*********************
     * Getters & Setters *
     *********************/

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public List<String> getRouteNames() {
        return routeNames;
    }

    public void setRouteNames(List<String> routeNames) {
        this.routeNames = routeNames;
    }

    public void addRouteName(String routeName) {
        this.routeNames.add(routeName);
    }

    public List<OCTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<OCTrip> trips) {
        this.trips = trips;
    }

    public void addTrip(OCTrip trip) {
        this.trips.add(trip);
    }
}