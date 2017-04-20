package com.easyshop.controller;

import com.easyshop.model.AddressModel;
import com.easyshop.model.UserModel;
import com.easyshop.model.CardModel;
import com.easyshop.repository.AddressRepository;
import com.easyshop.repository.CardRepository;
import com.easyshop.repository.SequenceRepository;
import com.easyshop.repository.UserRepository;
import com.easyshop.util.EasyShopUtil;
import com.easyshop.util.ProfileUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by pavan on 2/6/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {
    public final static Logger logger = Logger.getLogger("ProfileController.class");
    private static final String X_AUTH_TOKEN= "X-AUTH-TOKEN";

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    SequenceRepository sequenceRepository;

    @RequestMapping(value = "/custDetails", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomerDetails(HttpServletRequest request, @RequestParam(value= "id", required = false, defaultValue ="0" ) Long id){
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        if(id == 0){
            return ResponseEntity.ok(userRepository.findAll());
        }else {
            return ResponseEntity.ok(ProfileUtil.getCustomerDetails(id, userRepository, addressRepository, cardRepository));
        }
    }

    @RequestMapping(value = "/custDetails", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateCustomerStatus(HttpServletRequest request, @RequestBody UserModel userModel){
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        userRepository.save(userModel);
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(value = "/address", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity udpateAddress(HttpServletRequest request, @RequestBody AddressModel addressModel) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        addressRepository.save(addressModel);
        JSONObject response = new JSONObject();
        response.put("status",true);
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/address", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getAddresses(HttpServletRequest request, @RequestParam(name = "addressId", required = false, defaultValue = "0") long addressId){
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        return ResponseEntity.ok(addressRepository.findByAddressId(addressId));
    }

    @RequestMapping(value = "/address", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addAddress(HttpServletRequest request, @RequestBody AddressModel addressModel) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        addressModel.setAddressId(EasyShopUtil.getMaxId("card","cardId",sequenceRepository));
        addressModel = addressRepository.save(addressModel);
        return ResponseEntity.ok(addressModel);
    }

    @RequestMapping(value = "/address", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAddress(HttpServletRequest request, @RequestParam(name = "addressId") long addressId) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        addressRepository.delete(addressRepository.findByAddressId(addressId));
        JSONObject response = new JSONObject();
        response.put("status",true);
        return ResponseEntity.ok(addressRepository.findAll());
    }

    @RequestMapping(value = "/card", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity udpateCards(HttpServletRequest request, @RequestBody CardModel cardModel) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        cardRepository.save(cardModel);
        JSONObject response = new JSONObject();
        response.put("status",true);
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/card", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addCards(HttpServletRequest request, @RequestBody CardModel cardModel) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        cardModel.setCardId(EasyShopUtil.getMaxId("card","cardId",sequenceRepository));
        cardModel = cardRepository.save(cardModel);
        return ResponseEntity.ok(cardModel);
    }

    @RequestMapping(value = "/card", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCards(HttpServletRequest request, @RequestParam(name = "cardId") long cardId) throws Exception{
        if(!EasyShopUtil.isValidCustomer(userRepository, request)){
            return ResponseEntity.badRequest().body("Invalid Auth Token");
        }
        cardRepository.delete(cardRepository.findByCardId(cardId));
        JSONObject response = new JSONObject();
        response.put("status",true);
        return ResponseEntity.ok(response.toString());
    }

}
