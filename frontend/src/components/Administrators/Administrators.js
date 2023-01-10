import React, { Component } from "react";
import classes from './Administrators.module.css';
import ClinicAdministrator from './Administrator/Administrator';
import {Link, withRouter} from "react-router-dom";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTimes} from "@fortawesome/free-solid-svg-icons";
import authService from "../../services/auth-service";

class Administrators extends Component {
    
    state = {
        administrators: [],
        otherAdministrators: [],
        showPopup: false
    }
    
    componentDidMount() {
        if(this.props.isCca) {
            axios
                .get("/clinic-centre-administrators")
                .then((response) => {
                    console.log(response.data)
                    this.setState({
                        administrators: response.data
                    });
                })
                .catch(error => console.error(error));
        } else {
            axios
                .get("/clinic-administrators/clinic/" + this.props.match.params.id)
                .then((response) => {
                    console.log(response.data)
                    this.setState({
                        administrators: response.data
                    });
                })
                .catch(error => console.error(error));
        }
    }
    
    showOtherAdminsHandler = () => {
        axios
            .get("/clinic-administrators/not/clinic/" + this.props.match.params.id)
            .then((response) => {
                console.log(response.data)
                this.setState({
                    otherAdministrators: response.data,
                    showPopup: true
                });
            })
            .catch(error => console.error(error));
    }
    
    addAdminHandler = id => {
        let admin = this.state.otherAdministrators.find(admin => admin.id === id);
        admin = { ...admin, clinicId: Number(this.props.match.params.id)};
        
        axios
            .put("/clinic-administrators/", admin)
            .then((response) => {
                this.setState( {
                    administrators: [...this.state.administrators, admin],
                    showPopup: false
                });
            })
            .catch(error => console.error(error));
    }
    
    render() {
        
        let administrators = <h5 style={{textAlign: "center"}}>There are no clinic administrators!</h5>
        if(this.state.administrators.length > 0) {
            administrators = this.state.administrators.map(admin => <ClinicAdministrator admin={admin} isCca={this.props.isCca}/>);
        }
        
        let showPopup = null;
        if(this.state.showPopup) {
            let otherAdministrators = <h5>Every administrator is in this clinic already.</h5>;
            if(this.state.otherAdministrators.length > 0)
                otherAdministrators = this.state.otherAdministrators.map(admin => <ClinicAdministrator admin={admin}  btnClick={this.addAdminHandler} choose/>);
    
            showPopup =
                <>
                    <div className={classes.backgroundOverlay} onClick={() => this.setState({ showPopup: false })}></div>
                    <div className={classes.addAdminPopup}>
                        <FontAwesomeIcon icon={faTimes} size="1x" onClick={() => this.setState({ showPopup: false })} className={classes.closePopupBtn}/>
                        <h3>Choose administrator</h3>
                        {otherAdministrators}
                        {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && <Link to="/clinic-administrators/create" className={classes.addAdminBtn}>
                            <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginTop: "1.4rem", marginRight: "5px"}} />
                            <span>Add new clinic administrator</span>
                        </Link>}
                    </div>
                </>
        }
        
        return (
            <>
                <h1 className="my-5 text-center">{this.props.isCca ? "Clinic centre administrators" : "Clinic administrators"}</h1>
                {administrators}
                {(this.props.isCca && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR") && <div className="d-flex justify-content-center">
                    <Link to="/clinic-centre-administrators/create" className={classes.addAdminBtn}>
                        <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
                        <span>Add new clinic centre administrator</span>
                    </Link>
                </div>}
                {(!this.props.match.params.id && !this.props.isCca && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR") && <div className="d-flex justify-content-center">
                    <Link to="/clinic-administrators/create" className={classes.addAdminBtn}>
                        <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
                        <span>Add new clinic administrator</span>
                    </Link>
                </div>}
                {this.props.match.params.id && <div className="d-flex justify-content-center">
                    <button className={classes.addAdminBtn} onClick={this.showOtherAdminsHandler}>Add clinic administrator</button>
                </div>}
                {showPopup}
            </>
        );
    }
}

export default withRouter(Administrators);