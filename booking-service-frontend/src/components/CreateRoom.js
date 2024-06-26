import React, { useState } from 'react';

const CreateRoom = () =>{
    const [roomName, setRoomName] = useState('');
    const [capacity, setCapacity] = useState('');

    const handleSubmit = async(e) =>{
        e.preventDefault();

        const room = {
            roomName,
            capacity: parseInt(capacity)
        };

        try{
            const response = await fetch('http://localhost:8080/rooms', {
                method: 'POST',
                headers: {
                    'Content-type':'application/json'
                },
                body: JSON.stringify(room)
            });

            if(!response.ok){
                throw new Error('Failed to create room')
            }

            const data = await response.json();
            console.log("Room Created Succesfully:", data)
            setRoomName('') 
            setCapacity('')
        } catch(error){
            console.error('Error:', error)
        }
    };

    return(
        <div className="form-component">
            <h2>Create A Room</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="roomName">Room Name:</label>
                    <input
                        type="text"
                        id="roomName"
                        value={roomName}
                        onChange={(e) => setRoomName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="capacity">Capacity:</label>
                    <input
                        type="number"
                        id="capacity"
                        value={capacity}
                        onChange={(e) => setCapacity(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default CreateRoom;