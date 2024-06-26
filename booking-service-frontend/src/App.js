import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import NavBar from './components/NavBar';
import Home from './components/Home';
import BookingForm from './components/BookingForm';
import AvailableRooms from './components/AvailableRooms';
import CreateRoom from './components/CreateRoom';

function App() {
  return (
      <Router>
        <div className='App'>
        <NavBar/>
          <Routes>
            <Route path="/" element = {<Home/>}/>
            <Route path="/book" element={
              <div className='book-container'>
                <BookingForm/>
                <AvailableRooms/>
              </div>
            } />
            <Route path="/rooms" element={
              <div className='book-container'>
                <CreateRoom/>
                <AvailableRooms/>
              </div>
            } />
          </Routes>
      </div>
      </Router>
  );
}

export default App;
