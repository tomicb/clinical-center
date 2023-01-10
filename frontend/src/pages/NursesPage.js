import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import Nurses from "../components/Nurses/Nurses";


const nursesPage = () => {
    
    return (
        <StandardLayout>
            <Container>
                <Nurses />
            </Container>
        </StandardLayout>
    );
}

export default nursesPage;