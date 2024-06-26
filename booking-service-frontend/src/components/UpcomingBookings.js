import { useState, useEffect } from "react";

const UpcomingBookings = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const response = await fetch('http://localhost:8080/allUpcoming');
                if (!response.ok) {
                    throw new Error("Failed to fetch bookings");
                }
                const data = await response.json();
                setBookings(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBookings();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="bookings-list">
            <h2>Upcoming Room Bookings</h2>
            <ul>
                {bookings.map((booking) => (
                    <li key={booking.bookingID} className="booking-item">
                        <h3>{booking.roomName}</h3>
                        <p>Date: {booking.dateOfBooking}</p>
                        <p>Time From: {booking.timeFrom}</p>
                        <p>Time To : {booking.timeTo}</p>
                        <p>Booked by: {booking.user.name}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UpcomingBookings;