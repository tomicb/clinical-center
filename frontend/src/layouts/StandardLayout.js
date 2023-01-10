import React from 'react';
import Navbar from './../components/Navbar/Navbar';
import Footer from './../components/Footer/Footer';

const standardLayout = props => (
    <>
        <Navbar navLinks={props.navLinks}/>
        <main style={{minHeight: "66vh"}}>
            {props.children}
        </main>
        <Footer/>
    </>
)

export default standardLayout;