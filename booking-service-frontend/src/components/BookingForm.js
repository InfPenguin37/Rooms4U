import React, {useState} from 'react';

const BookingForm = () =>{
    const [userID, setUserID] = useState('');
    const [roomID, setRoomID] = useState('');
    const [dateOfBooking, setDateOfBooking] = useState('');
    const [timeFrom, setTimeFrom] = useState('');
    const [timeTo, setTimeTo] = useState('');
    const [purpose, setPurpose] = useState('');

    const handleSubmit = async(e)=>{
        e.preventDefault();

        const booking = {
            userID: parseInt(userID),
            roomID: parseInt(roomID),
            dateOfBooking,
            timeFrom,
            timeTo,
            purpose
        };

        try{
            const response = await fetch('http://localhost:8080/book', {
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(booking)
            });

            if (!response.ok) {
                throw new Error('Failed to create booking');
            }

            const data = await response.json();
            console.log('Booking created successfully:', data);
            // Reset form fields after successful submission
            setUserID('');
            setRoomID('');
            setDateOfBooking('');
            setTimeFrom('');
            setTimeTo('');
            setPurpose('');
        } catch (error) {
            console.error('Error:', error);
        }
    };

        return (
            <div className="form-component">
                <h2>Book a Room</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="userID">User ID:</label>
                        <input
                            type="number"
                            id="userID"
                            value={userID}
                            onChange={(e) => setUserID(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="roomID">Room ID:</label>
                        <input
                            type="number"
                            id="roomID"
                            value={roomID}
                            onChange={(e) => setRoomID(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="dateOfBooking">Date of Booking:</label>
                        <input
                            type="date"
                            id="dateOfBooking"
                            value={dateOfBooking}
                            onChange={(e) => setDateOfBooking(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="timeFrom">Time From:</label>
                        <input
                            type="time"
                            id="timeFrom"
                            value={timeFrom}
                            onChange={(e) => setTimeFrom(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="timeTo">Time To:</label>
                        <input
                            type="time"
                            id="timeTo"
                            value={timeTo}
                            onChange={(e) => setTimeTo(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="purpose">Purpose:</label>
                        <input
                            type="text"
                            id="purpose"
                            value={purpose}
                            onChange={(e) => setPurpose(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit">Submit</button>
                </form>
            </div>
        );
};

export default BookingForm;