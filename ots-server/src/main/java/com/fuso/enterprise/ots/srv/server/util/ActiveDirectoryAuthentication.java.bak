package com.fuso.enterprise.ipt.srv.server.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveDirectoryAuthentication {

	private static final String CONTEXT_FACTORY_CLASS = "com.sun.jndi.ldap.LdapCtxFactory";

    static String BASE_NAME = ",DC=apac,DC=corpdir,DC=net";
    
    static String BASE_NAME_VALID = "OU=Users,OU=COP,OU=A575,DC=apac,DC=corpdir,DC=net";

	private String ldapServerUrls[];

	private int lastLdapUrlIndex;

	private final String domainName;
	
	private Logger logger = LoggerFactory.getLogger(ActiveDirectoryAuthentication.class);

	public ActiveDirectoryAuthentication(String domainName) {
		this.domainName = domainName.toUpperCase();

		try {
			logger.info("Domain name :"+ domainName);
			System.out.println("Domain name :"+ domainName);
			ldapServerUrls = nsLookup(domainName);
			logger.info("ldapServerUrls :"+ ldapServerUrls);
			System.out.println("ldapServerUrls :"+ ldapServerUrls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastLdapUrlIndex = 0;
	}

	public boolean authenticate(String username, String password) throws LoginException {
		if (ldapServerUrls == null || ldapServerUrls.length == 0) {
			throw new AccountException("Unable to find ldap servers");
		}
		if (username == null || password == null || username.trim().length() == 0 || password.trim().length() == 0) {
			throw new FailedLoginException("Username or password is empty");
		}
		int retryCount = 0;
		int currentLdapUrlIndex = lastLdapUrlIndex;
		do {
			retryCount++;
			try {
				Hashtable<Object, Object> env = new Hashtable<Object, Object>();
				env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_CLASS);
				env.put(Context.PROVIDER_URL, ldapServerUrls[currentLdapUrlIndex]);
				env.put(Context.SECURITY_PRINCIPAL, username + "@" + domainName);
				env.put(Context.SECURITY_CREDENTIALS, password);
				logger.info("Before DirContext");
				logger.info("DirContext env url :" + env.get(Context.PROVIDER_URL) );
				logger.info("DirContext env username:" + env.get(Context.SECURITY_PRINCIPAL) );
				logger.info("DirContext env password (will remove once the debug is done):" + env.get(Context.SECURITY_CREDENTIALS) );
				System.out.println("Before DirContext");
				System.out.println("DirContext env url :" + env.get(Context.PROVIDER_URL) );
				System.out.println("DirContext env username:" + env.get(Context.SECURITY_PRINCIPAL) );
				System.out.println("DirContext env password (will remove once the debug is done):" + env.get(Context.SECURITY_CREDENTIALS) );
				DirContext ctx = new InitialDirContext(env);
				logger.info("After DirContext");
				//Below commented code is an attempt to fetch Information.
				
				/*
				System.out.println("Printing logged in user attributes");
				fetchUserAttributes(ctx, username);
				System.out.println("Printing yelands information");
				this.fetchUserAttributes(ctx, "YELANDS");
				*/
				ctx.close();
				lastLdapUrlIndex = currentLdapUrlIndex;
				return true;
			} catch (CommunicationException exp) {
				// TODO you can replace with log4j or slf4j API
				exp.printStackTrace();
				System.out.println(exp.getMessage());
				// if the exception of type communication we can assume the AD
				// is not reachable hence retry can be attempted with next
				// available AD
				if (retryCount < ldapServerUrls.length) {
					currentLdapUrlIndex++;
					if (currentLdapUrlIndex == ldapServerUrls.length) {
						currentLdapUrlIndex = 0;
					}
					continue;
				}
				return false;
			} catch (Throwable throwable) {
				System.out.println(throwable.getMessage());
				throwable.printStackTrace();
				return false;
			}
		} while (true);
	}

	public Attributes fetchUserAttributes(DirContext cntxt , String username) {
		Attributes attributes = null;
		try {
			System.out.println("fetching: " + username);
			DirContext o = (DirContext) cntxt.lookup("cn=" + "JAISJOY" + BASE_NAME);
			System.out.println("search done\n");
			attributes = o.getAttributes("");
			for (NamingEnumeration<?> ae = attributes.getAll(); ae.hasMoreElements();) {
				Attribute attr = (Attribute) ae.next();
				String attrId = attr.getID();
				for (NamingEnumeration<?> vals = attr.getAll(); vals.hasMore();) {
					String thing = vals.next().toString();
					System.out.println(attrId + ": " + thing);
				}
			}
		} catch (Exception e) {
			System.out.println(" fetch error: " + e);
			System.exit(-1);
		}
		return attributes;
	}

	private static String[] nsLookup(String argDomain) throws Exception {
		try {
			Hashtable<Object, Object> env = new Hashtable<Object, Object>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
			env.put("java.naming.provider.url", "dns:");
			DirContext ctx = new InitialDirContext(env);
			Attributes attributes = ctx.getAttributes(String.format("_ldap._tcp.%s", argDomain),
					new String[] { "srv" });
			// try thrice to get the KDC servers before throwing error
			for (int i = 0; i < 3; i++) {
				Attribute a = attributes.get("srv");
				if (a != null) {
					List<String> domainServers = new ArrayList<String>();
					NamingEnumeration<?> enumeration = a.getAll();
					while (enumeration.hasMoreElements()) {
						String srvAttr = (String) enumeration.next();
						// the value are in space separated 0) priority 1)
						// weight 2) port 3) server
						String values[] = srvAttr.toString().split(" ");
						domainServers.add(String.format("ldap://%s:%s", values[3], values[2]));
					}
					String domainServersArray[] = new String[domainServers.size()];
					domainServers.toArray(domainServersArray);
					return domainServersArray;
				}
			}
			throw new Exception("Unable to find srv attribute for the domain " + argDomain);
		} catch (NamingException exp) {
			throw new Exception("Error while performing nslookup. Root Cause: " + exp.getMessage(), exp);
		}
	}
	
	public boolean validateUser(String username, String password, String validateUserName) throws AccountException, FailedLoginException {
		boolean validated = false;
		if (ldapServerUrls == null || ldapServerUrls.length == 0) {
			throw new AccountException("Unable to find ldap servers");
		}
		if (username == null || password == null || username.trim().length() == 0 || password.trim().length() == 0) {
			throw new FailedLoginException("Username or password is empty");
		}
		int currentLdapUrlIndex = lastLdapUrlIndex;
    	try {
    		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_CLASS);
			env.put(Context.PROVIDER_URL, ldapServerUrls[currentLdapUrlIndex]);
			env.put(Context.SECURITY_PRINCIPAL, username + "@" + domainName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext ctx = new InitialDirContext(env);
			
			SearchResult searchRes = findAccountByAccountName( ctx,  BASE_NAME_VALID,  validateUserName);
			
			if(searchRes!=null) {
				validated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validated;
	}
	
	 public SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName) throws NamingException {
         String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

         SearchControls searchControls = new SearchControls();
         searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
         NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

         SearchResult searchResult = null;
         if(results.hasMoreElements()) {
              searchResult = (SearchResult) results.nextElement();
             //make sure there is not another item available, there should be only 1 match
             if(results.hasMoreElements()) {
                 System.err.println("Matched multiple users for the accountName: " + accountName);
                 return null;
             }
         }
         
         return searchResult;
     }
}