package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.saudiMart.Product.Model.Address;
import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.AddressRepository;
import com.saudiMart.Product.Model.Address.AddressType;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Page<Address> getAllAddresses(Pageable pageable) {
 return addressRepository.findAll(pageable);
    }

    public Address getAddressById(String id) throws ProductException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ProductException("Address not found with id: " + id));
    }

 public Page<Address> searchAddresses(AddressType addressType, String companyName, String city, String state, String postalCode, String country, Pageable pageable) {
        if (addressType != null) {
            return addressRepository.findByAddressType(addressType, pageable);
        } else if (companyName != null && !companyName.isEmpty()) {
            return addressRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable);
        } else if (city != null && !city.isEmpty()) {
            return addressRepository.findByCityContainingIgnoreCase(city, pageable);
        } else if (state != null && !state.isEmpty()) {
            return addressRepository.findByStateContainingIgnoreCase(state, pageable);
        } else if (postalCode != null && !postalCode.isEmpty()) {
            return addressRepository.findByPostalCodeContainingIgnoreCase(postalCode, pageable);
        } else if (country != null && !country.isEmpty()) {
            return addressRepository.findByCountryContainingIgnoreCase(country, pageable);
        }
        // If no specific search criteria are provided, return all addresses with pagination
        return addressRepository.findAll(pageable);
    }


 public Page<Address> getAddressesByUser(Users user, Pageable pageable) {
 return addressRepository.findByUser(user, pageable);
    }

    public Address createAddress(Address address) throws ProductException {
        if (address == null) {
            throw new ProductException("Address cannot be null");
        }
        return addressRepository.save(address);
    }

    public Address updateAddress(String id, Address addressDetails) throws ProductException {
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

    public void deleteAddress(String id) throws ProductException {
        if (!addressRepository.existsById(id)) {
            throw new ProductException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}