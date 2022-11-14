package com.controller;

import com.dto.ResponseDto;
import com.dto.UserDto;
import com.service.JwtUserDetailsService;
import com.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.config.JwtTokenUtil;
import com.model.JwtRequest;
import com.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private ResponseDto responseDto;

    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        String status = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        if(status=="00") {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

            final String token = jwtTokenUtil.generateToken(userDetails);

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Success");
            responseDto.setContent(new JwtResponse(token));
            return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
        } else if (status=="03") {
            responseDto.setCode(VarList.RSP_INVALID_CRED);
            responseDto.setMessage("Invalid Credentials");
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.UNAUTHORIZED);
        }else{
            responseDto.setCode(VarList.RSP_BAD_REQUEST);
            responseDto.setMessage("Bad Request");
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/v1/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        try{
            String res = userDetailsService.save(user);
            if(res.equals("00")){
                responseDto.setCode(VarList.RSP_SUCCESS);
                responseDto.setMessage("Success");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
            }
            else if(res.equals("06")){
                responseDto.setCode(VarList.RSP_BAD_REQUEST);
                responseDto.setMessage("Bad request");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
            else if(res.equals("04")){
                responseDto.setCode(VarList.RSP_DUPLICATED);
                responseDto.setMessage("User Already Exists");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
            else{
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Error");
                responseDto.setContent(null);
                return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            responseDto.setCode(VarList.RSP_FAIL);
            responseDto.setMessage(e.getMessage());
            responseDto.setContent(null);
            return new ResponseEntity(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return VarList.RSP_SUCCESS;
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            return VarList.RSP_BAD_REQUEST;
        }catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
            return VarList.RSP_INVALID_CRED;
        }
    }
}