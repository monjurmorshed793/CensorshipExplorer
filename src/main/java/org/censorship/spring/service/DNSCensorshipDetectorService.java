package org.censorship.spring.service;

import org.censorship.spring.domains.CensorshipResponse;
import org.censorship.spring.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DNSCensorshipDetectorService {
    private final Logger log = LoggerFactory.getLogger(DNSCensorshipDetectorService.class);

    public CensorshipResponse detectDNSCensorshipStatus(String webAddress) throws Exception{
        CensorshipResponse censorshipResponse = new CensorshipResponse();

        if(checkWhetherIpAddressListIsSameOrNot(webAddress)){
            censorshipResponse.setStatus(Status.NOT_CENSORED);
            censorshipResponse.setMessage("NO DNS BASED CENSORSHIP DETECTED");
        }else{
            censorshipResponse.setStatus(Status.CENSORED);
            censorshipResponse.setMessage("DNS BASED CENSORSHIP DETECTED");
        }

        return censorshipResponse;
    }

    public String refactorWebAddress(String webAddress){
        webAddress = webAddress.replace("http://","");
        webAddress = webAddress.replace("https://","");
        if(webAddress.indexOf("/")>0)
            webAddress = webAddress.substring(0, webAddress.indexOf("/"));

        return webAddress;
    }

    private boolean checkWhetherIpAddressListIsSameOrNot(String webAddress) throws Exception{
        webAddress = refactorWebAddress(webAddress);
        List<String> hostIpAddresses = resolveIpAddresses(webAddress);
        List<String> cloudIpAddresses = resolveIpAddressesFromCloud(webAddress);
        return hostIpAddresses.containsAll(cloudIpAddresses);
    }

    public List<String> resolveIpAddresses(String webAddress)throws Exception{
        webAddress = refactorWebAddress(webAddress);
        InetAddress[] addresses = InetAddress.getAllByName(webAddress);
        List<InetAddress> addressList = Arrays.asList(addresses);
        return addressList.stream().map(a->a.getHostAddress()).collect(Collectors.toList());
    }

    private List<String> resolveIpAddressesFromCloud(String webAddress){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<String >> ipAddressList = restTemplate.exchange("https://censorship-test.azurewebsites.net/resolve?address=" + webAddress, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        return ipAddressList.getBody();
    }
}
