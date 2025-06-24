package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.AddressRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) throws ProductException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ProductException("Address not found with id: " + id));
    }

    public List<Address> getAddressesByUser(Users user) {
        return addressRepository.findByUser(user);
    }

    public Address createAddress(Address address) throws ProductException {
        if (address == null) {
            throw new ProductException("Address cannot be null");
        }
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressDetails) throws ProductException {
        if (addressDetails == null) {
            throw new ProductException("Address details cannot be null for update");
        }
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            if (addressDetails.getAddressType() != null)
                address.setAddressType(addressDetails.getAddressType());
            if (addressDetails.getCompanyName() != null)
                address.setCompanyName(addressDetails.getCompanyName());
            if (addressDetails.getStreetAddress1() != null)
                address.setStreetAddress1(addressDetails.getStreetAddress1());
            if (addressDetails.getStreetAddress2() != null)
                address.setStreetAddress2(addressDetails.getStreetAddress2());
            if (addressDetails.getCity() != null)
                address.setCity(addressDetails.getCity());
            if (addressDetails.getState() != null)
                address.setState(addressDetails.getState());
            if (addressDetails.getPostalCode() != null)
                address.setPostalCode(addressDetails.getPostalCode());
            if (addressDetails.getCountry() != null)
                address.setCountry(addressDetails.getCountry());
            if (addressDetails.getIsDefault() != null)
                address.setIsDefault(addressDetails.getIsDefault());
            // Assuming you don't want to change the user association via update
            return addressRepository.save(address);
        }
        throw new ProductException("Address not found with id: " + id);
    }

    public void deleteAddress(Long id) throws ProductException {
        if (!addressRepository.existsById(id)) {
            throw new ProductException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}