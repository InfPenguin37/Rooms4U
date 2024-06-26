import { useEffect, useState } from "react";

const AvailableRooms = () => {
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect (() =>{
        const fetchRooms = async () => {
            try {
                const response = await fetch("http://localhost:8080/rooms/all");
                if(!response.ok){
                    throw new Error("Failed to fetch rooms");
                }
                const data = await response.json();
                setRooms(data);
            } catch (error){
                setError(error.message);
            } finally{
                setLoading(false);
            }
        };
        fetchRooms();
    }, []);

    if(loading){
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="room-list">
            <h2>Available Rooms</h2>
            <ul>
                {rooms.map((room) => (
                    <li key={room.roomID} className="room-item">
                        <h3>{room.roomName}</h3>
                        <p>Capacity: {room.capacity}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AvailableRooms;