import React, { Component } from "react";
import classes from "./RegistrationRequests.css";
import RegistrationRequest from "./RegistrationRequest/RegistrationRequest";
import axios from "axios";
import {Container} from "react-bootstrap";

class RegistrationRequests extends Component {
    state = {
        users: []
    };
    
    componentDidMount() {
        axios.get('/users/requests')
            .then(response => {
                console.log(response.data)
                this.setState({users: response.data});
            })
            .catch(error => console.log(error));
    }
    
    approveRequestHandler = (id) => {
        const user = this.state.users.find(user => user.id === id);
        axios.put('/users/requests/approve/', user)
            .then(resonse => {
                if(resonse.status === 200 && resonse.data.status === "NOT_VERIFIED") {
                    let users = [...this.state.users];
                    users = users.filter(user => user.id !== id);
                    this.setState({users: users});
                }
            })
            .catch(error => console.log(error));
    }

    rejectRequestHandler = (id) => {
        const user = this.state.users.find(user => user.id === id);
        axios.put('/users/requests/reject/', user)
            .then(resonse => {
                if(resonse.status === 200 && resonse.data.status === "REJECTED") {
                    let users = [...this.state.users];
                    users = users.filter(user => user.id !== id);
                    this.setState({users: users});
                }
            })
            .catch(error => console.log(error));
    }
    
    render() {
        
        let registrationRequests = <h4 className="text-center">There are no registration requests!</h4>;
        if(this.state.users.length > 0) {
            registrationRequests = this.state.users.map(user => (
                <RegistrationRequest
                    key={user.id}
                    id={user.id}
                    fullName={user.firstName + " " + user.lastName}
                    jmbg={user.jmbg}
                    address={user.address}
                    onApprove={this.approveRequestHandler}
                    onReject={this.rejectRequestHandler}/>));
        }
        
        return (
            <Container fluid className={classes.content}>
                <h1 className={["text-center", "my-5"].join(' ')}>Registration requests</h1>
                <div className={["mb-5"].join(' ')}>
                    {registrationRequests}
                </div>
            </Container>
        )
    }
}

export default RegistrationRequests;