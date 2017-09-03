package com.stylefeng.guns.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.stylefeng.guns.common.persistence.model.Student;
import com.stylefeng.guns.core.shiro.factory.IShiro;
import com.stylefeng.guns.core.shiro.factory.ShiroFactroy;

public class StudentDbRealm extends AuthorizingRealm {


	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)  {
		// TODO Auto-generated method stub
			IShiro shiroFactory = ShiroFactroy.me();
			UsernamePasswordUsertypeToken myToken = (UsernamePasswordUsertypeToken) token;
			Student student = shiroFactory.student(myToken.getUsername());
			ShiroUser shiroUser = shiroFactory.shiroStudent(student);
			return shiroFactory.infoStudent(shiroUser,student,super.getName());
	}
	/**
	 * 设置认证加密方式
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
		md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
		md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
		super.setCredentialsMatcher(md5CredentialsMatcher);
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
			return new SimpleAuthorizationInfo();
	}
}
