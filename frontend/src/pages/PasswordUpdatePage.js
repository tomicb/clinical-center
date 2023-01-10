import React from "react";
import StandardLayout from "./../layouts/StandardLayout";
import { Container } from "react-bootstrap";
import PasswordUpdate from "../components/Authorization/PasswordUpdate/PasswordUpdate";


const passwordUpdatePage = () => {
    
    return (
        <StandardLayout>
            <Container>
                <PasswordUpdate />
            </Container>
        </StandardLayout>
    );
}

export default passwordUpdatePage;