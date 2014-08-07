package be.aca.portal.scopedprefs;

import com.liferay.portal.kernel.exception.SystemException;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

public interface ScopedPreferencesService {

	/**
	 * Returns portlet preferences that are shared across all portlet instances in the portal instance or company the
	 * given portlet request was fired. Each user sees the same set of preferences.
	 */
	PortletPreferences getPortalWidePreferences(PortletRequest portletRequest) throws SystemException;

	/**
	 * Returns portlet preferences that are shared across all portlet instances in the site the given portlet request
	 * was fired. Each user sees the same set of preferences.
	 */
	PortletPreferences getSiteWidePreferences(PortletRequest portletRequest) throws SystemException;

	/**
	 * Returns portlet preferences that are specific for the portlet instance the given portlet request is applied to.
	 * Two portlets on different pages, whether in different sites or not, will have different preferences. Each user
	 * sees the same set of preferences for a certain portlet instance.
	 */
	PortletPreferences getPortletSpecificPreferences(PortletRequest portletRequest) throws SystemException;

	/**
	 * Returns portlet preferences that are only visible for a specific user. The preferences will be the same for all
	 * portlets the user has access to, even if they live on different sites.
	 */
	PortletPreferences getUserSpecificPreferences(PortletRequest portletRequest) throws SystemException;

	/**
	 * Returns portlet preferences that are only visible for a specific user. Two portlets on different pages will have
	 * different preferences.
	 */
	PortletPreferences getUserSpecificPortletSpecificPreferences(PortletRequest portletRequest) throws SystemException;

}
