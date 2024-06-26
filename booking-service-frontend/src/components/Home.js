import UpcomingBookings from './UpcomingBookings';
import AvailableRooms from './AvailableRooms';

const Home = () => {
    return(
        <div className='main-container'>
            <UpcomingBookings/>
            <AvailableRooms/>
        </div>
    )
}

export default Home;