import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import Doctors from "../components/Doctors/Doctors";


const doctorsPage = () => {
    
    return (
        <StandardLayout>
            <Container>
                <Doctors />
            </Container>
        </StandardLayout>
    );
}

export default doctorsPage;