import React, {Component} from "react";
import classes from "./CreateUpdateAdministrator.module.css";
import Select from 'react-select'
import authService from "../../../services/auth-service";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faUser,
    faIdCard,
    faHome,
    faEnvelope,
    faUnlock,
    faVenusMars,
    faClinicMedical
} from "@fortawesome/free-solid-svg-icons";
import {withRouter, Link} from "react-router-dom";
import moment from 'moment'
import {Col} from "react-bootstrap";
import axios from "axios";
import {isNumber} from "chart.js/helpers";

class CreateUpdateAdministrator extends Component {
    
    dataGender = [
        {value: "MALE", label: "Male"},
        {value: "FEMALE", label: "Female"}
    ]
    
    customStyles = {
        control: (provided, state) => ({
            ...provided,
            border: 'none',
            boxShadow: "none",
            borderRadius: '15px'
        }),
        menu: (provided) => ({
            ...provided,
            marginTop: 0,
            border: '1px solid rgb(227,175,188)'
        }),
        option: (provided, state) => ({
            ...provided,
            border: 'none',
            backgroundColor: state.isSelected ? 'rgb(227,175,188)' : '#fff',
            color: '#000',
            ':hover': {
                backgroundColor: '#5D001E',
                color: '#fff'
            }
        })
    }
    
    state = {
        id: null,
        firstName: "",
        lastName: "",
        jmbg: "",
        originalJmbg: "",
        address: "",
        email: "",
        originalEmail: "",
        gender: "MALE",
        clinicId: null,
        errorValidation: {
            firstName: false,
            lastName: false,
            jmbg: false,
            jmbgTaken: false,
            address: false,
            email: false,
            emailTaken: false,
            password: false,
            clinic: false
        },
        creating: false,
        clinicAdmin: false,
        clinics: [],
        showPasswordChangeBtn: false
    };
    
    componentDidMount() {
        const creating = this.props.creating;
        const clinicAdmin = this.props.clinicAdmin;
        this.setState({creating: creating, clinicAdmin: clinicAdmin});
        
        if (!creating) {
            const id = this.props.match.params.id;
            
            if (id === undefined) {
                if (clinicAdmin) {
                    axios
                        .get("/clinic-administrators/clinicAdminInfo")
                        .then((response) => {
                            console.log(response.data);
                            this.setState({
                                id: response.data.id,
                                firstName: response.data.firstName,
                                lastName: response.data.lastName,
                                jmbg: response.data.jmbg,
                                originalJmbg: response.data.jmbg,
                                address: response.data.address,
                                email: response.data.email,
                                originalEmail: response.data.email,
                                gender: response.data.gender,
                                clinicId: response.data.clinicId,
                                showPasswordChangeBtn: true
                            });
                        })
                        .catch((error) => console.error(error));
                } else {
                    axios
                        .get("/clinic-centre-administrators/clinicCentreAdminInfo")
                        .then((response) => {
                            console.log(response.data);
                            this.setState({
                                id: response.data.id,
                                firstName: response.data.firstName,
                                lastName: response.data.lastName,
                                jmbg: response.data.jmbg,
                                originalJmbg: response.data.jmbg,
                                address: response.data.address,
                                email: response.data.email,
                                originalEmail: response.data.email,
                                gender: response.data.gender,
                            });
                        })
                        .catch((error) => console.error(error));
                }
            } else {
                if (clinicAdmin) {
                    axios
                        .get("/clinic-administrators/" + id)
                        .then((response) => {
                            console.log(response.data);
                            this.setState({
                                id: response.data.id,
                                firstName: response.data.firstName,
                                lastName: response.data.lastName,
                                jmbg: response.data.jmbg,
                                originalJmbg: response.data.jmbg,
                                address: response.data.address,
                                email: response.data.email,
                                originalEmail: response.data.email,
                                gender: response.data.gender,
                                clinicId: response.data.clinicId,
                            });
                        })
                        .catch((error) => console.error(error));
                } else {
                    axios
                        .get("/clinic-centre-administrators/" + id)
                        .then((response) => {
                            console.log(response.data);
                            this.setState({
                                id: response.data.id,
                                firstName: response.data.firstName,
                                lastName: response.data.lastName,
                                jmbg: response.data.jmbg,
                                originalJmbg: response.data.jmbg,
                                address: response.data.address,
                                email: response.data.email,
                                originalEmail: response.data.email,
                                gender: response.data.gender,
                            });
                        })
                        .catch((error) => console.error(error));
                }
            }
        }
        
        if (clinicAdmin) {
            axios
                .get("/clinics/")
                .then((response) => {
                    const clinicsData = response.data.clinics.map(clinic => ({value: clinic.id, label: clinic.name}));
                    this.setState({
                        clinics: clinicsData
                    });
                })
                .catch((error) => console.error(error));
        }
    };
    
    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }
    
    submitHandler = event => {
        event.preventDefault();
        
        const data = {
            id: this.state.id,
            firstName: this.state.firstName.trim(),
            lastName: this.state.lastName.trim(),
            jmbg: this.state.jmbg.trim(),
            address: this.state.address.trim(),
            email: this.state.email.trim().toLowerCase(),
            password: this.state.password,
            gender: this.state.gender,
            clinicId: this.state.clinicId
        }
        
        let error =  {
            firstName: false,
            lastName: false,
            jmbg: false,
            jmbgTaken: false,
            address: false,
            email: false,
            emailTaken: false,
            password: false,
            clinic: false
        }
        
        if (!data.firstName) {
            error.firstName = true;
        }
        
        if (!data.lastName) {
            error.lastName = true;
        }
        
        if (!data.jmbg || data.jmbg.length !== 13 || !isNumber(data.jmbg)) {
            error.jmbg = true;
        }
        
        const DD = data.jmbg.substring(0, 2);
        const MM = data.jmbg.substring(2, 4);
        const GGGG = data.jmbg[4] === "0" ? "2" + data.jmbg.substring(4, 7) : "1" + data.jmbg.substring(4, 7);
        if (!moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid()) {
            error.jmbg = true;
        }
        
        if (!data.address) {
            error.address = true;
        }
        
        if (!this.validateEmail(data.email)) {
            error.email = true;
        }
    
        if (!data.password || data.password.length < 6) {
            error.password = true;
        }
    
        if (data.clinicId === null) {
            error.clinic = true;
        }
        
        this.setState({errorValidation: {
                firstName: error.firstName,
                lastName: error.lastName,
                jmbg: error.jmbg,
                jmbgTaken: error.jmbgTaken,
                address: error.address,
                email: error.email,
                emailTaken: error.emailTaken,
                password: error.password,
                clinic: error.clinic
        }})
        
        console.log("STIGAO")
        
        if (!data.firstName ||
            !data.lastName ||
            !data.jmbg ||
            data.jmbg.length !== 13 ||
            !isNumber(data.jmbg) ||
            !moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid() ||
            !data.address ||
            !this.validateEmail(data.email) ||
            ((!data.password ||
            data.password.length < 6) && this.state.creating) ||
            (data.clinicId === null && this.state.clinicAdmin && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR"))
            return;
        console.log("STIGAO")
        
        if (this.state.creating) {
            if (this.state.clinicAdmin) {
                axios
                    .post("/clinic-administrators/", data)
                    .then((response) => {
                        window.history.go(-1);
                    })
                    .catch((error) => console.log(error));
            } else {
                axios
                    .post("/clinic-centre-administrators/", data)
                    .then((response) => {
                        window.location.replace("/clinic-centre-administrators");
                    })
                    .catch((error) => console.log(error));
            }
        } else {
            if (this.state.clinicAdmin) {
                axios
                    .put("/clinic-administrators/", data)
                    .then((response) => {
                        window.history.go(-1);
                    })
                    .catch((error) => console.log(error));
            } else {
                axios
                    .put("/clinic-centre-administrators/", data)
                    .then((response) => {
                        window.location.replace("/clinic-centre-administrators");
                    })
            }
        }
    }
    
    firstNameInputHandler = event => {
        this.setState({
            firstName: event.target.value,
            errorValidation: {...this.state.errorValidation, firstName: event.target.value.trim() === ""}
        });
    }
    
    lastNameInputHandler = event => {
        this.setState({
            lastName: event.target.value,
            errorValidation: {...this.state.errorValidation, lastName: event.target.value.trim() === ""}
        });
    }
    
    jmbgInputHandler = event => {
        const jmbg = event.target.value.trim();
        
        const DD = jmbg.substring(0, 2);
        const MM = jmbg.substring(2, 4);
        const GGGG = jmbg[4] === "0" ? "2" + jmbg.substring(4, 7) : "1" + jmbg.substring(4, 7);
        
        const error = jmbg.length !== 13 || !moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid();
        
        this.setState({jmbg: jmbg, errorValidation: {...this.state.errorValidation, jmbg: error}});
    }
    
    checkJmbgHandler = () => {
        if(this.state.originalJmbg !== this.state.jmbg) {
            if (this.state.clinicAdmin) {
                axios
                    .get("/clinic-administrators/check/jmbg/" + this.state.jmbg)
                    .then((response) => {
                        console.log(response.data)
                        this.setState({errorValidation: {...this.state.errorValidation, jmbgTaken: response.data}})
                    })
                    .catch((error) => console.log(error));
            } else {
                axios
                    .get("/clinic-centre-administrators/check/jmbg/" + this.state.jmbg)
                    .then((response) => {
                        console.log(response.data)
                        this.setState({errorValidation: {...this.state.errorValidation, jmbgTaken: response.data}})
                    })
                    .catch((error) => console.log(error));
            }
        }
    }
    
    addressInputHandler = event => {
        this.setState({
            address: event.target.value,
            errorValidation: {...this.state.errorValidation, address: event.target.value.trim() === ""}
        });
    }
    
    genderSelectHandler = gender => {
        this.setState({gender: gender.value})
    }
    
    emailInputHandler = event => {
        this.setState({
            email: event.target.value.trim(),
            errorValidation: {...this.state.errorValidation, email: event.target.value.trim() === ""}
        });
    }
    
    checkEmailHandler = () => {
        if(this.state.originalEmail !== this.state.email) {
            if (this.state.clinicAdmin) {
                axios
                    .get("/clinic-administrators/check/email/" + this.state.email)
                    .then((response) => {
                        console.log(response.data)
                        this.setState({
                            errorValidation: {
                                ...this.state.errorValidation,
                                email: !this.validateEmail(this.state.email.trim()),
                                emailTaken: response.data
                            }
                        })
                    })
                    .catch((error) => console.log(error));
            } else {
                axios
                    .get("/clinic-centre-administrators/check/email/" + this.state.email)
                    .then((response) => {
                        console.log(response.data)
                        this.setState({
                            errorValidation: {
                                ...this.state.errorValidation,
                                email: !this.validateEmail(this.state.email.trim()),
                                emailTaken: response.data
                            }
                        })
                    })
                    .catch((error) => console.log(error));
            }
        }
    }
    
    passwordInputHandler = event => {
        this.setState({
            password: event.target.value,
            errorValidation: {...this.state.errorValidation, password: event.target.value.length < 6}
        });
    }
    
    checkClinicHandler = () => {
        this.setState({errorValidation: {...this.state.errorValidation, clinic: this.state.clinicId === null}});
    }
    
    clinicSelectHandler = clinic => {
        this.setState({clinicId: clinic.value})
    }
    
    render() {
        
        return (
            <>
                <h1 className="my-5 text-center">
                    {this.state.creating ? "Create" : "Update"} clinic {!this.state.clinicAdmin && "centre"} administrator
                </h1>
                <form>
                    <div
                        className={[
                            classes.inputItem,
                            this.state.errorValidation.firstName
                                ? classes.errorBorder
                                : "",
                        ].join(" ")}
                    >
                        <FontAwesomeIcon
                            icon={faUser}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="text"
                            placeholder="First name"
                            value={this.state.firstName}
                            onChange={(event) => this.firstNameInputHandler(event)}
                        />
                    </div>
                    <div
                        className={[
                            classes.inputItem,
                            this.state.errorValidation.lastName
                                ? classes.errorBorder
                                : "",
                        ].join(" ")}
                    >
                        <FontAwesomeIcon
                            icon={faUser}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={this.state.lastName}
                            onChange={(event) => this.lastNameInputHandler(event)}
                        />
                    </div>
                    <div
                        className={[
                            classes.inputItem,
                            this.state.errorValidation.jmbg ||
                            this.state.errorValidation.jmbgTaken
                                ? classes.errorBorder
                                : "",
                        ].join(" ")}
                    >
                        {this.state.errorValidation.jmbgTaken ? (
                            <span>JMBG taken!</span>
                        ) : null}
                        <FontAwesomeIcon
                            icon={faIdCard}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="number"
                            placeholder="JMBG"
                            value={this.state.jmbg}
                            onChange={(event) => this.jmbgInputHandler(event)}
                            onBlur={this.checkJmbgHandler}
                        />
                    </div>
                    <div
                        className={[
                            classes.inputItem,
                            this.state.errorValidation.address ? classes.errorBorder : "",
                        ].join(" ")}
                    >
                        <FontAwesomeIcon
                            icon={faHome}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="text"
                            placeholder="Address"
                            value={this.state.address}
                            onChange={(event) => this.addressInputHandler(event)}
                        />
                    </div>
                    <div className={classes.inputItem}>
                        <FontAwesomeIcon
                            icon={faVenusMars}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <Select
                            options={this.dataGender}
                            className={classes.selectItem}
                            styles={this.customStyles}
                            value={this.dataGender.filter(
                                (obj) => obj.value === this.state.gender
                            )}
                            onChange={(gender) => this.genderSelectHandler(gender)}
                            placeholder="Select gender"
                        />
                    </div>
                    <div
                        className={[
                            classes.inputItem,
                            this.state.errorValidation.email ||
                            this.state.errorValidation.emailTaken
                                ? classes.errorBorder
                                : "",
                        ].join(" ")}
                    >
                        {this.state.errorValidation.emailTaken ? (
                            <span>Email taken!</span>
                        ) : null}
                        <FontAwesomeIcon
                            icon={faEnvelope}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="email"
                            placeholder="Email"
                            value={this.state.email}
                            onChange={(event) => this.emailInputHandler(event)}
                            onBlur={this.checkEmailHandler}
                        />
                    </div>
                    { this.state.creating && <div className={[
                            classes.inputItem,
                            this.state.errorValidation.password
                                ? classes.errorBorder
                                : "",
                        ].join(" ")}
                    >
                        <FontAwesomeIcon
                            icon={faUnlock}
                            size="1x"
                            className={classes.inputItemIcon}
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            value={this.state.password}
                            onChange={(event) => this.passwordInputHandler(event)}
                        />
                    </div>}
                    {this.state.clinicAdmin && authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" &&
                        <div className={[
                                classes.inputItem,
                                this.state.errorValidation.clinic ? classes.errorBorder : "",
                            ].join(" ")}>
                            <FontAwesomeIcon
                                icon={faClinicMedical}
                                size="1x"
                                className={classes.inputItemIcon}
                            />
                            <Select
                                options={this.state.clinics}
                                className={classes.selectItem}
                                styles={this.customStyles}
                                value={this.state.clinics.filter(
                                    (obj) => obj.value === this.state.clinicId
                                )}
                                onChange={(clinic) => this.clinicSelectHandler(clinic)}
                                onBlur={this.checkClinicHandler}
                                placeholder="Select clinic"
                            />
                        </div>}
                    <div className="d-flex justify-content-center">
                        <input
                            type="submit"
                            className={classes.submitBtn}
                            value={this.state.clinicAdmin ? "Save clinic administrator" : "Save clinic centre administrator"}
                            onClick={(event) => this.submitHandler(event)}
                        />
                    </div>
                    {this.state.showPasswordChangeBtn && <Col
                        lg={12}
                        md={12}
                        sm={12}
                        xs={12}
                        className="text-center"
                        id="change"
                    >
                        <Link to="/password-update">
                            <button
                                className="btn btn-info m-5 justify-center"
                                onClick={this.changePasswordHandler}
                            >
                                Change password
                            </button>
                        </Link>
                    </Col>}
                </form>
            </>
        );
    }
}

export default withRouter(CreateUpdateAdministrator);
