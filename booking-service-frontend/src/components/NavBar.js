import { useState } from "react";
import {Squash as Hamburger} from "hamburger-react";
import {useClickAway} from "react-use";
import {useRef} from "react"
import { AnimatePresence, motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

const NavBar =  () => {
    const [isOpen, setOpen] = useState(false);
    const ref = useRef(null);
    const navigate = useNavigate();

    useClickAway(ref, () => setOpen(false));

    const handleNavigate = (path) => {
        setOpen(false);
        navigate(path);
    };

    return (
        <header>
                <div className="container navbar"> 
                    <Hamburger label="hamburger" toggled={isOpen} size = {30} toggle ={setOpen}/>
                    <h1>RoomBookings4U</h1>
                    <button><h2>LOGIN</h2></button>
                </div>
                <AnimatePresence>
                    {isOpen && (
                        <motion.div
                        initial={{opacity: 0, y: -20}}
                        animate={{opacity: 1, y: 0}}
                        exit={{opacity: 0, y: -20}}
                        className="hamburger-menu"
                        ref={ref}
                        >
                            <button onClick={() => handleNavigate('/')}>Home</button>
                            <button onClick={() => handleNavigate('/book')}>Book Room</button>
                            <button onClick={() => handleNavigate('/rooms')}>Create Room</button>
                        </motion.div>
                    )}
                </AnimatePresence>
        </header>
    );
};
export default NavBar