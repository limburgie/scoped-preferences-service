package be.aca.portal.scopedprefs.impl;

import be.aca.portal.scopedprefs.ScopedPreferencesService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

public class ScopedPreferencesServiceImpl implements ScopedPreferencesService {

	private PortletPreferencesLocalService portletPreferencesService;
	private Portal portal;

	public ScopedPreferencesServiceImpl() {
		portletPreferencesService = PortletPreferencesLocalServiceUtil.getService();
		portal = PortalUtil.getPortal();
	}

	public PortletPreferences getPortalWidePreferences(PortletRequest portletRequest) throws SystemException {
		int ownerType = ResourceConstants.SCOPE_COMPANY;
		long ownerId = portal.getCompanyId(portletRequest);
		long plid = 0;

		return getPreferences(portletRequest, ownerType, ownerId, plid);
	}

	public PortletPreferences getSiteWidePreferences(PortletRequest portletRequest) throws SystemException {
		int ownerType = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		long ownerId = themeDisplay(portletRequest).getScopeGroupId();
		long plid = 0;

		return getPreferences(portletRequest, ownerType, ownerId, plid);
	}

	public PortletPreferences getPortletSpecificPreferences(PortletRequest portletRequest) throws SystemException {
		int ownerType = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		long ownerId = themeDisplay(portletRequest).getScopeGroupId();
		long plid = getPlid(portletRequest);

		return getPreferences(portletRequest, ownerType, ownerId, plid);
	}

	public PortletPreferences getUserSpecificPortletSpecificPreferences(PortletRequest portletRequest) throws SystemException {
		int ownerType = ResourceConstants.SCOPE_INDIVIDUAL;
		long ownerId = portal.getUserId(portletRequest);
		long plid = getPlid(portletRequest);

		return getPreferences(portletRequest, ownerType, ownerId, plid);
	}

	public PortletPreferences getUserSpecificPreferences(PortletRequest portletRequest) throws SystemException {
		int ownerType = ResourceConstants.SCOPE_INDIVIDUAL;
		long ownerId = portal.getUserId(portletRequest);
		long plid = 0;

		return getPreferences(portletRequest, ownerType, ownerId, plid);
	}

	private long getPlid(PortletRequest portletRequest) {
		return themeDisplay(portletRequest).getPlid();
	}

	private PortletPreferences getPreferences(PortletRequest portletRequest, int ownerType, long ownerId, long plid) throws SystemException {
		long companyId = portal.getCompanyId(portletRequest);
		String portletId = (String) portletRequest.getAttribute(WebKeys.PORTLET_ID);
		return portletPreferencesService.getPreferences(companyId, ownerId, ownerType, plid, portletId);
	}

	private ThemeDisplay themeDisplay(PortletRequest portletRequest) {
		return ((ThemeDisplay) portletRequest.getAttribute("LIFERAY_SHARED_THEME_DISPLAY"));
	}

}
