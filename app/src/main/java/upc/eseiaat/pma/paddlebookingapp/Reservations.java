package upc.eseiaat.pma.paddlebookingapp;

/**
 * Created by Eugenia on 09/01/2018.
 */

public class Reservations {

    String reservationId;
    String reservationHour;
    String reservationDay;
    String reservationMonth;
    String user1_id;
    String user2_id;


    public Reservations() {

    }

    public Reservations(String reservation_id, String reservation_hour, String reservation_day,
                        String reservation_month, String user1_id, String user2_id) {

        this.reservationId = reservation_id;
        this.reservationHour = reservation_hour;
        this.reservationDay = reservation_day;
        this.reservationMonth = reservation_month;
        this.user1_id = user1_id;
        this.user2_id = user2_id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getReservationHour() {
        return reservationHour;
    }

    public String getReservationDay() {
        return reservationDay;
    }

    public String getReservationMonth() {
        return reservationMonth;
    }

    public String getUser1_id() {
        return user1_id;
    }

    public String getUser2_id() {
        return user2_id;
    }
}
